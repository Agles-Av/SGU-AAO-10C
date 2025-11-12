import React, { useEffect, useState } from 'react';
import { Button, Modal,ModalHeader, TextInput, Label, ModalBody } from 'flowbite-react';
import { AlertHelper } from '../utilities/AlertHelper';

const UserFormModal = ({ show, onClose, onSave, initialData = null }) => {
    const [nombre, setNombre] = useState('');
    const [apellidos, setApellidos] = useState('');
    const [email, setEmail] = useState('');
    const [saving, setSaving] = useState(false);

    useEffect(() => {
        if (initialData) {
            setNombre(initialData.nombre || '');
            setApellidos(initialData.apellidos || '');
            setEmail(initialData.email || '');
        } else {
            setNombre('');
            setApellidos('');
            setEmail('');
        }
    }, [initialData, show]);

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!nombre || !apellidos || !email) {
            AlertHelper.showAlert('Todos los campos son obligatorios', 'error');
            return;
        }

        setSaving(true);
        try {
            await onSave({ nombre, apellidos, email });
            // onSave should close the modal when appropriate
        } catch (err) {
            // onSave already handles alerts in most cases, but catch to avoid unhandled rejections.
            console.error(err);
        } finally {
            setSaving(false);
        }
    };

    return (
        <Modal show={show} size="md" popup onClose={onClose}>
            <ModalHeader />
            <ModalBody>
                <div className="space-y-6 px-6 pb-4 sm:pb-6 lg:px-8 xl:pb-8">
                    <h3 className="text-xl font-medium text-gray-900">{initialData ? 'Editar Usuario' : 'Nuevo Usuario'}</h3>

                    <form className="flex flex-col gap-4" onSubmit={handleSubmit}>
                        <div>
                            <div className="mb-2 block">
                                <Label htmlFor="nombre" value="Nombre" />
                            </div>
                            <TextInput id="nombre" value={nombre} onChange={(e) => setNombre(e.target.value)} required />
                        </div>

                        <div>
                            <div className="mb-2 block">
                                <Label htmlFor="apellidos" value="Apellidos" />
                            </div>
                            <TextInput id="apellidos" value={apellidos} onChange={(e) => setApellidos(e.target.value)} required />
                        </div>

                        <div>
                            <div className="mb-2 block">
                                <Label htmlFor="email" value="Correo" />
                            </div>
                            <TextInput id="email" type="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
                        </div>

                        <div className="flex justify-end gap-2">
                            <Button color="gray" onClick={onClose} type="button">Cancelar</Button>
                            <Button type="submit" disabled={saving}>{saving ? 'Guardando...' : 'Guardar'}</Button>
                        </div>
                    </form>
                </div>
            </ModalBody>
        </Modal>
    );
};

export default UserFormModal;
