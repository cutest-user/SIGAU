import { nombreCompleto } from '../api/formatos'
import useAuth from '../auth/useAuth'

function Header({ onMenu }) {
    const { usuario, cerrarSesion } = useAuth()

    return (
        <header className="bg-success text-white px-3 px-md-4 py-3 d-flex justify-content-between align-items-center">
            <div className="d-flex align-items-center gap-3">
                <button
                    className="btn btn-outline-light btn-sm d-md-none"
                    type="button"
                    onClick={onMenu}
                >
                    Menú
                </button>

                <div>
                    <h1 className="h4 mb-0">SIGAU</h1>
                    <small className="d-none d-sm-inline">
                        Sistema de Gestión Integral de Arbolado Urbano
                    </small>
                </div>
            </div>

            <div className="text-end">
                <div className="small">{nombreCompleto(usuario)}</div>
                <button
                    className="btn btn-link btn-sm text-white p-0"
                    type="button"
                    onClick={cerrarSesion}
                >
                    Cerrar sesión
                </button>
            </div>
        </header>
    )
}

export default Header
