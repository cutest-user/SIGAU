import { api } from './cliente'

export const UBICACION_VACIA = {
    calle: '',
    numero: '',
    barrio: '',
    comuna: '',
    referencia: ''
}

export function guardarUbicacion(valores) {
    return api.post('/api/ubicaciones', {
        calle: valores.calle,
        numero: valores.numero || null,
        barrio: valores.barrio || null,
        comuna: valores.comuna || null,
        referencia: valores.referencia || null
    })
}
