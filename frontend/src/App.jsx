import PantallaDetalleDemanda from './pages/demandas/PantallaDetalleDemanda'
import Sidebar from './components/Sidebar'
import Header from './components/Header'
import PantallaDemandas from './pages/demandas/PantallaDemandas'
import FormularioDemanda from './pages/demandas/FormularioDemanda'
import { useState } from 'react'
import './App.css'


function App() {
  const [demandaSeleccionada, setDemandaSeleccionada] = useState(null)
  const [pagina, setPagina] = useState('Inicio')
  const [mostrarFormularioDemanda, setMostrarFormularioDemanda] = useState(false)

  const cambiarPagina = (nombre) => {
    setPagina(nombre)
    setMostrarFormularioDemanda(false)
  }

  return (
      <div className="container-fluid p-0">
        <Header />
        <div className="d-flex">
          <Sidebar
              pagina={pagina}
              onCambiarPagina={cambiarPagina}
          />


          <main className="flex-grow-1 p-4">
            {pagina === 'Inicio' ? (
                <>
                  <h2 className="h3 mb-4">Panel principal</h2>

                  <div className="row g-3">
                    {['Demandas', 'Ejemplares', 'Seguimiento'].map((opcion) => (
                        <div className="col-md-4" key={opcion}>
                          <div className="card h-100">
                            <div className="card-body">
                              <h3 className="h5 card-title">{opcion}</h3>
                              <p className="card-text">
                                Acceso al módulo de {opcion.toLowerCase()}.
                              </p>
                              <button
                                  className="btn btn-outline-success"
                                  onClick={() => cambiarPagina(opcion)}
                              >
                                Ver {opcion.toLowerCase()}
                              </button>
                            </div>
                          </div>
                        </div>
                    ))}
                  </div>
                </>
            ) : pagina === 'Demandas' ? (
                mostrarFormularioDemanda ? (
                    <FormularioDemanda
                        onCancelar={() => setMostrarFormularioDemanda(false)}
                    />
                ) : (
                    <PantallaDemandas
                        onNuevaDemanda={() => setMostrarFormularioDemanda(true)}
                    />
                )
            ) : (
                <>
                  <h2 className="h3 mb-3">{pagina}</h2>
                  <p>Pantalla del módulo {pagina.toLowerCase()}.</p>
                </>
            )}
          </main>

        </div>
      </div>
  )
}

export default App
