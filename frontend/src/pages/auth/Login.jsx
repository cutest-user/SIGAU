import { useState } from 'react'
import { mensajeDeError } from '../../api/cliente'
import Alerta from '../../components/Alerta'
import useAuth from '../../auth/useAuth'

function Login() {
    const { iniciarSesion } = useAuth()
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [error, setError] = useState('')
    const [enviando, setEnviando] = useState(false)

    const enviar = async (evento) => {
        evento.preventDefault()
        setError('')
        setEnviando(true)

        try {
            await iniciarSesion(email.trim(), password)
        } catch (fallo) {
            setError(mensajeDeError(fallo))
            setEnviando(false)
        }
    }

    return (
        <div className="min-vh-100 d-flex flex-column">
            <header className="bg-success text-white px-4 py-3">
                <h1 className="h4 mb-0">SIGAU</h1>
                <small>Sistema de Gestión Integral de Arbolado Urbano</small>
            </header>

            <main className="flex-grow-1 d-flex align-items-center justify-content-center p-3">
                <div className="card w-100" style={{ maxWidth: '400px' }}>
                    <div className="card-body p-4">
                        <h2 className="h4 mb-3">Ingresar</h2>

                        <Alerta>{error}</Alerta>

                        <form onSubmit={enviar}>
                            <div className="mb-3">
                                <label className="form-label">Email</label>
                                <input
                                    type="email"
                                    className="form-control"
                                    value={email}
                                    onChange={(e) => setEmail(e.target.value)}
                                    autoComplete="username"
                                    required
                                />
                            </div>

                            <div className="mb-4">
                                <label className="form-label">Contraseña</label>
                                <input
                                    type="password"
                                    className="form-control"
                                    value={password}
                                    onChange={(e) => setPassword(e.target.value)}
                                    autoComplete="current-password"
                                    required
                                />
                            </div>

                            <button
                                className="btn btn-success w-100"
                                disabled={enviando}
                            >
                                {enviando ? 'Ingresando...' : 'Ingresar'}
                            </button>
                        </form>
                    </div>
                </div>
            </main>
        </div>
    )
}

export default Login
