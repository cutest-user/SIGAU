import { useState } from 'react'
import { mensajeDeError } from '../api/cliente'

export default function useAccion(alTerminar) {
    const [error, setError] = useState('')
    const [enviando, setEnviando] = useState(false)

    const ejecutar = async (funcion) => {
        setError('')
        setEnviando(true)

        try {
            await funcion()
            if (alTerminar) alTerminar()
        } catch (fallo) {
            setError(mensajeDeError(fallo))
        } finally {
            setEnviando(false)
        }
    }

    return { ejecutar, error, enviando }
}
