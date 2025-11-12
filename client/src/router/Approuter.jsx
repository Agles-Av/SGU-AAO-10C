import React from 'react'

import { Suspense, lazy } from "react"
import { Route, createBrowserRouter, createRoutesFromElements, RouterProvider } from "react-router-dom"
import SpinnerLazy from '../utilities/SpinnerLazy';


const Users = lazy(() => import("../features/Users"));


const Approuter = () => {

    const router = createBrowserRouter(
        createRoutesFromElements(
            <Route
            index
            path='/'
            element={
                <Suspense fallback={<SpinnerLazy/>}>
                    <Users/>
                </Suspense>
            }
            />
        )
    );

    return <RouterProvider router={router}/>
}

export default Approuter