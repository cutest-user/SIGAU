import { useEffect, useState } from 'react'
import { alExpirar, api, guardarToken, hayToken } from '../api/cliente'
import { ContextoAuth } from './contexto'

const CLAVE_USUARIO = 'sigau.usuario'
const ROL_INSPECTOR = 1
const ROL_ADMINISTRATIVO = 2

function leerUsuarioGuardado() {
    if (!hayToken()) return null

    try {
        return JSON.parse(sessionStorage.getItem(CLAVE_USUARIO))
    } catch {
        return null
    }
}

function AuthProvider({ children }) {
    const [usuario, setUsuario] = useState(leerUsuarioGuardado)

    useEffect(() => {
        alExpirar(() => {
            sessionStorage.removeItem(CLAVE_USUARIO)
            setUsuario(null)
        })
    }, [])

    const iniciarSesion = async (email, password) => {
        const respuesta = await api.post('/api/auth/login', { email, password })

        guardarToken(respuesta.token)

        const datos = {
            id: respuesta.id,
            nombre: respuesta.nombre,
            apellido: respuesta.apellido,
            email: respuesta.email,
            rolId: respuesta.rolId
        }

        sessionStorage.setItem(CLAVE_USUARIO, JSON.stringify(datos))
        setUsuario(datos)
    }

    const cerrarSesion = () => {
        guardarToken(null)
        sessionStorage.removeItem(CLAVE_USUARIO)
        setUsuario(null)
    }

    const valor = {
        usuario,
        iniciarSesion,
        cerrarSesion,
        esInspector: usuario?.rolId === ROL_INSPECTOR,
        esAdministrativo: usuario?.rolId === ROL_ADMINISTRATIVO
    }

    return (
        <ContextoAuth.Provider value={valor}>
            {children}
        </ContextoAuth.Provider>
    )
}

export default AuthProvider
