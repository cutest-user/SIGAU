import { useState } from 'react'
import useAuth from './auth/useAuth'
import Header from './components/Header'
import Sidebar from './components/Sidebar'
import Login from './pages/auth/Login'
import PantallaCertificaciones from './pages/certificaciones/PantallaCertificaciones'
import FormularioDemanda from './pages/demandas/FormularioDemanda'
import PantallaDemandas from './pages/demandas/PantallaDemandas'
import PantallaDetalleDemanda from './pages/demandas/PantallaDetalleDemanda'
import PantallaDerivaciones from './pages/derivaciones/PantallaDerivaciones'
import PantallaEjemplares from './pages/ejemplares/PantallaEjemplares'
import Inicio from './pages/inicio/Inicio'
import PantallaInspecciones from './pages/inspecciones/PantallaInspecciones'
import PantallaIntervenciones from './pages/intervenciones/PantallaIntervenciones'
import PantallaReportes from './pages/reportes/PantallaReportes'
import PantallaSeguimiento from './pages/seguimiento/PantallaSeguimiento'
import PantallaUsuarios from './pages/usuarios/PantallaUsuarios'

function App() {
  const { usuario, esAdministrativo } = useAuth()
  const [pagina, setPagina] = useState('Inicio')
  const [demandaId, setDemandaId] = useState(null)
  const [nuevaDemanda, setNuevaDemanda] = useState(false)
  const [menuAbierto, setMenuAbierto] = useState(false)

  if (!usuario) {
    return <Login />
  }

  const cambiarPagina = (nombre) => {
    setPagina(nombre)
    setDemandaId(null)
    setNuevaDemanda(false)
    setMenuAbierto(false)
  }

  const abrirDemanda = (id) => {
    setPagina('Demandas')
    setNuevaDemanda(false)
    setDemandaId(id)
  }

  const renderDemandas = () => {
    if (demandaId) {
      return (
        <PantallaDetalleDemanda
          key={demandaId}
          id={demandaId}
          onVolver={() => setDemandaId(null)}
        />
      )
    }

    if (nuevaDemanda) {
      return (
        <FormularioDemanda
          onCancelar={() => setNuevaDemanda(false)}
          onGuardada={abrirDemanda}
        />
      )
    }

    return (
      <PantallaDemandas
        onNuevaDemanda={() => setNuevaDemanda(true)}
        onVerDetalle={abrirDemanda}
      />
    )
  }

  const paginas = {
    Inicio: <Inicio onIr={cambiarPagina} />,
    Demandas: renderDemandas(),
    Ejemplares: <PantallaEjemplares />,
    Inspecciones: <PantallaInspecciones />,
    Intervenciones: <PantallaIntervenciones onVerDemanda={abrirDemanda} />,
    Seguimiento: <PantallaSeguimiento onVerDemanda={abrirDemanda} />,
    Derivaciones: <PantallaDerivaciones onVerDemanda={abrirDemanda} />,
    Certificaciones: <PantallaCertificaciones onVerDemanda={abrirDemanda} />,
    Reportes: <PantallaReportes />,
    Usuarios: esAdministrativo ? <PantallaUsuarios /> : <Inicio onIr={cambiarPagina} />
  }

  return (
    <div className="min-vh-100 d-flex flex-column">
      <Header onMenu={() => setMenuAbierto(!menuAbierto)} />

      <div className="d-flex flex-column flex-md-row flex-grow-1">
        <Sidebar
          pagina={pagina}
          abierto={menuAbierto}
          onCambiarPagina={cambiarPagina}
        />

        <main className="flex-grow-1 p-3 p-md-4" style={{ minWidth: 0 }}>
          {paginas[pagina]}
        </main>
      </div>
    </div>
  )
}

export default App
