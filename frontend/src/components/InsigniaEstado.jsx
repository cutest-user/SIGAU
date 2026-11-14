import { textoEstado } from '../api/formatos'

const COLORES = {
    INGRESADA: 'secondary',
    EN_GESTION: 'primary',
    CERTIFICADA: 'success',
    CANCELADA: 'dark',
    PENDIENTE: 'secondary',
    PLANIFICADA: 'info',
    EN_EJECUCION: 'warning',
    REALIZADA: 'success',
    ACTIVO: 'success',
    EXTRAIDO: 'dark',
    APROBADA: 'success',
    OBSERVADA: 'warning',
    ALTA: 'danger',
    MEDIA: 'warning',
    BAJA: 'secondary'
}

function InsigniaEstado({ valor }) {
    const color = COLORES[valor] ?? 'secondary'
    const oscuro = color === 'warning' || color === 'info'

    return (
        <span className={`badge text-bg-${color} ${oscuro ? 'text-dark' : ''}`}>
            {textoEstado(valor)}
        </span>
    )
}

export default InsigniaEstado
