import { useEffect, useState } from 'react'
import { mensajeDeError } from '../api/cliente'

export default function useDatos(cargar) {
    const [estado, setEstado] = useState({
        datos: null,
        error: null,
        cargando: true
    })
    const [version, setVersion] = useState(0)

    useEffect(() => {
        let vigente = true

        cargar()
            .then((datos) => {
                if (vigente) setEstado({ datos, error: null, cargando: false })
            })
            .catch((error) => {
                if (vigente) {
                    setEstado({
                        datos: null,
                        error: mensajeDeError(error),
                        cargando: false
                    })
                }
            })

        return () => {
            vigente = false
        }
    }, [cargar, version])

    return {
        ...estado,
        recargar: () => setVersion((actual) => actual + 1)
    }
}
