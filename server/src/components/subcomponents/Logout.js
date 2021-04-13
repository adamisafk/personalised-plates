import React from 'react'
import { Redirect } from 'react-router'

import authService from '../../services/auth.service'

export default function Logout() {
    authService.logout()
    return (
        <Redirect to='/' />
    )
}
