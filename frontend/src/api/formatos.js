export function formatearFecha(valor) {
    if (!valor) return '—'

    const texto = String(valor)

    if (/^\d{4}-\d{2}-\d{2}$/.test(texto)) {
        const [anio, mes, dia] = texto.split('-')
        return `${dia}/${mes}/${anio}`
    }

    const fecha = new Date(texto)

    if (Number.isNaN(fecha.getTime())) return '—'

    return fecha.toLocaleDateString('es-AR')
}

export function textoUbicacion(ubicacion) {
    if (!ubicacion) return 'Sin ubicación'

    const calle = [ubicacion.calle, ubicacion.numero].filter(Boolean).join(' ')
    const zona = [ubicacion.barrio, ubicacion.comuna && `Comuna ${ubicacion.comuna}`]
        .filter(Boolean)
        .join(', ')

    return [calle, zona].filter(Boolean).join(' · ') || 'Sin ubicación'
}

export function nombreCompleto(usuario) {
    if (!usuario) return '—'

    return [usuario.nombre, usuario.apellido].filter(Boolean).join(' ')
}

export function textoEstado(estado) {
    if (!estado) return '—'

    const texto = estado.replaceAll('_', ' ').toLowerCase()

    return texto.charAt(0).toUpperCase() + texto.slice(1)
}
