import React, { useEffect, useState } from 'react';
import { Button, TextInput, Label } from 'flowbite-react';
import UserTable from '../../components/UserTable';
import UserFormModal from '../../components/UserFormModal';
import {
    fetchAllUsers,
    createUser,
    updateUser,
    deleteUser,
} from '../../services/userServices';
import { AlertHelper } from '../../utilities/AlertHelper';

const Users = () => {
    const [users, setUsers] = useState([]);
    const [loading, setLoading] = useState(false);
    const [filter, setFilter] = useState('');

    const [modalOpen, setModalOpen] = useState(false);
    const [editingUser, setEditingUser] = useState(null);

    const loadUsers = async () => {
        setLoading(true);
        try {
            const apiResponse = await fetchAllUsers();
            if (apiResponse && apiResponse.data) {
                setUsers(apiResponse.data);
            } else {
                setUsers([]);
            }
        } catch (e) {
            console.error(e);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        loadUsers();
    }, []);

    const handleAdd = () => {
        setEditingUser(null);
        setModalOpen(true);
    };

    const handleEdit = (user) => {
        setEditingUser(user);
        setModalOpen(true);
    };

    const handleDelete = async (id) => {
        if (!confirm('¿Eliminar usuario?')) return;

        try {
            const resp = await deleteUser(id);
            if (resp && resp.status >= 200 && resp.status < 300) {
                AlertHelper.showAlert(resp.message || 'Usuario eliminado', 'success');
                await loadUsers();
            }
        } catch (e) {
            console.error(e);
        }
    };

    const handleSave = async (payload) => {
        try {
            if (editingUser && editingUser.id) {
                const resp = await updateUser(editingUser.id, payload);
                if (resp && resp.status >= 200 && resp.status < 300) {
                    AlertHelper.showAlert(resp.message || 'Usuario actualizado', 'success');
                    setModalOpen(false);
                    await loadUsers();
                }
            } else {
                const resp = await createUser(payload);
                if (resp && resp.status >= 200 && resp.status < 300) {
                    AlertHelper.showAlert(resp.message || 'Usuario creado', 'success');
                    setModalOpen(false);
                    await loadUsers();
                }
            }
        } catch (e) {
            console.error(e);
        }
    };

    return (
        <div className="p-4">
            <div className="flex items-center justify-between mb-4">
                <div className="flex items-center gap-2">
                    <div>
                        <Label htmlFor="search">Buscar por nombre</Label>
                        <TextInput id="search" placeholder="Nombre..." value={filter} onChange={(e) => setFilter(e.target.value)} />
                    </div>
                </div>

                <div>
                    <Button onClick={handleAdd}>Añadir Nuevo Usuario</Button>
                </div>
            </div>

            <div className="mb-4">
                {loading ? <div>Cargando...</div> : <UserTable users={users} filter={filter} onEdit={handleEdit} onDelete={handleDelete} />}
            </div>

            <UserFormModal
                show={modalOpen}
                onClose={() => setModalOpen(false)}
                onSave={handleSave}
                initialData={editingUser}
            />
        </div>
    );
};

export default Users;
