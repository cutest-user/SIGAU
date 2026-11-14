import { useCallback, useState } from 'react'
import { api } from '../../api/cliente'
import useAuth from '../../auth/useAuth'
import Alerta from '../../components/Alerta'
import Cargando from '../../components/Cargando'
import useAccion from '../../hooks/useAccion'
import useDatos from '../../hooks/useDatos'

const ROLES = { 1: 'Inspector', 2: 'Administrativo' }
const FORMULARIO_VACIO = { nombre: '', apellido: '', email: '', password: '', rolId: '1' }

function FormularioUsuario({ onGuardado, onCancelar }) {
    const [valores, setValores] = useState(FORMULARIO_VACIO)
    const accion = useAccion(onGuardado)

    const cambiar = (campo) => (evento) =>
        setValores({ ...valores, [campo]: evento.target.value })

    const enviar = (evento) => {
        evento.preventDefault()
        accion.ejecutar(() => api.post('/api/usuarios', {
            ...valores,
            rolId: Number(valores.rolId),
            activo: true
        }))
    }

    return (
        <form onSubmit={enviar} className="card mb-4">
            <div className="card-body">
                <h3 className="h5 mb-3">Nuevo usuario</h3>

                <div className="row g-3">
                    <div className="col-md-6">
                        <label className="form-label">Nombre</label>
                        <input
                            className="form-control"
                            value={valores.nombre}
                            maxLength={100}
                            onChange={cambiar('nombre')}
                            required
                        />
                    </div>
                    <div className="col-md-6">
                        <label className="form-label">Apellido</label>
                        <input
                            className="form-control"
                            value={valores.apellido}
                            maxLength={150}
                            onChange={cambiar('apellido')}
                            required
                        />
                    </div>
                    <div className="col-md-6">
                        <label className="form-label">Email</label>
                        <input
                            type="email"
                            className="form-control"
                            value={valores.email}
                            maxLength={150}
                            onChange={cambiar('email')}
                            required
                        />
                    </div>
                    <div className="col-md-3">
                        <label className="form-label">Contraseña</label>
                        <input
                            type="password"
                            className="form-control"
                            value={valores.password}
                            minLength={8}
                            maxLength={72}
                            onChange={cambiar('password')}
                            autoComplete="new-password"
                            required
                        />
                    </div>
                    <div className="col-md-3">
                        <label className="form-label">Rol</label>
                        <select
                            className="form-select"
                            value={valores.rolId}
                            onChange={cambiar('rolId')}
                        >
                            <option value="1">Inspector</option>
                            <option value="2">Administrativo</option>
                        </select>
                    </div>
                </div>

                <Alerta>{accion.error}</Alerta>

                <div className="d-flex gap-2 mt-3">
                    <button className="btn btn-success" disabled={accion.enviando}>
                        Crear usuario
                    </button>
                    <button type="button" className="btn btn-outline-secondary" onClick={onCancelar}>
                        Cancelar
                    </button>
                </div>
            </div>
        </form>
    )
}

function PantallaUsuarios() {
    const { usuario: actual } = useAuth()
    const cargar = useCallback(() => api.get('/api/usuarios'), [])
    const { datos, error, cargando, recargar } = useDatos(cargar)
    const [mostrandoForm, setMostrandoForm] = useState(false)
    const cambio = useAccion(recargar)

    const alternarActivo = (usuario) =>
        cambio.ejecutar(() => api.put(`/api/usuarios/${usuario.id}`, {
            nombre: usuario.nombre,
            apellido: usuario.apellido,
            email: usuario.email,
            rolId: usuario.rolId,
            activo: !usuario.activo
        }))

    return (
        <div>
            <div className="d-flex justify-content-between align-items-center mb-4 flex-wrap gap-2">
                <div>
                    <h2 className="h3 mb-1">Usuarios</h2>
                    <p className="text-secondary mb-0">
                        Alta y administración de las personas que usan el sistema.
                    </p>
                </div>

                {!mostrandoForm && (
                    <button className="btn btn-success" onClick={() => setMostrandoForm(true)}>
                        + Nuevo usuario
                    </button>
                )}
            </div>

            {mostrandoForm && (
                <FormularioUsuario
                    onCancelar={() => setMostrandoForm(false)}
                    onGuardado={() => {
                        setMostrandoForm(false)
                        recargar()
                    }}
                />
            )}

            <Alerta>{error || cambio.error}</Alerta>

            <div className="card">
                <div className="card-body">
                    {cargando ? (
                        <Cargando />
                    ) : (
                        <div className="table-responsive">
                            <table className="table align-middle">
                                <thead>
                                <tr>
                                    <th>Nombre</th>
                                    <th>Email</th>
                                    <th>Rol</th>
                                    <th>Estado</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                {(datos ?? []).map((u) => (
                                    <tr key={u.id}>
                                        <td>{u.nombre} {u.apellido}</td>
                                        <td>{u.email}</td>
                                        <td>{ROLES[u.rolId]}</td>
                                        <td>
                                            <span className={`badge text-bg-${u.activo ? 'success' : 'secondary'}`}>
                                                {u.activo ? 'Activo' : 'Inactivo'}
                                            </span>
                                        </td>
                                        <td className="text-end">
                                            {u.id !== actual.id && (
                                                <button
                                                    className="btn btn-sm btn-outline-secondary"
                                                    disabled={cambio.enviando}
                                                    onClick={() => alternarActivo(u)}
                                                >
                                                    {u.activo ? 'Desactivar' : 'Activar'}
                                                </button>
                                            )}
                                        </td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                        </div>
                    )}
                </div>
            </div>
        </div>
    )
}

export default PantallaUsuarios
