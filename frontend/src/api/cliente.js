const URL_BASE = import.meta.env.VITE_API_URL ?? 'http://localhost:8080'
const CLAVE_TOKEN = 'sigau.token'

let token = sessionStorage.getItem(CLAVE_TOKEN)
let alExpirarSesion = null

export function guardarToken(valor) {
    token = valor

    if (valor) {
        sessionStorage.setItem(CLAVE_TOKEN, valor)
    } else {
        sessionStorage.removeItem(CLAVE_TOKEN)
    }
}

export function hayToken() {
    return Boolean(token)
}

export function alExpirar(funcion) {
    alExpirarSesion = funcion
}

function leerJson(texto) {
    try {
        return texto ? JSON.parse(texto) : null
    } catch {
        return null
    }
}

async function pedir(metodo, ruta, cuerpo) {
    const cabeceras = {}

    if (cuerpo !== undefined) {
        cabeceras['Content-Type'] = 'application/json'
    }

    if (token) {
        cabeceras.Authorization = `Bearer ${token}`
    }

    const respuesta = await fetch(`${URL_BASE}${ruta}`, {
        method: metodo,
        headers: cabeceras,
        body: cuerpo !== undefined ? JSON.stringify(cuerpo) : undefined
    })

    if (respuesta.status === 401 && token) {
        guardarToken(null)
        if (alExpirarSesion) alExpirarSesion()
    }

    const texto = await respuesta.text()
    const datos = leerJson(texto)

    if (!respuesta.ok) {
        const error = new Error(
            datos?.mensaje ?? 'No se pudo completar la operación.'
        )
        error.detalles = datos?.detalles ?? []
        error.estado = respuesta.status
        throw error
    }

    return datos
}

export const api = {
    get: (ruta) => pedir('GET', ruta),
    post: (ruta, cuerpo) => pedir('POST', ruta, cuerpo),
    put: (ruta, cuerpo) => pedir('PUT', ruta, cuerpo),
    patch: (ruta, cuerpo) => pedir('PATCH', ruta, cuerpo)
}

export function mensajeDeError(error) {
    if (error instanceof TypeError) {
        return 'No se pudo conectar con el servidor.'
    }

    if (error.detalles && error.detalles.length > 0) {
        return error.detalles.join(' · ')
    }

    return error.message
}
