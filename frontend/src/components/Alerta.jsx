function Alerta({ tipo = 'danger', children }) {
    if (!children) return null

    return (
        <div className={`alert alert-${tipo} py-2`} role="alert">
            {children}
        </div>
    )
}

export default Alerta
