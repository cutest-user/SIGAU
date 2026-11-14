import { useContext } from 'react'
import { ContextoAuth } from './contexto'

export default function useAuth() {
    return useContext(ContextoAuth)
}
