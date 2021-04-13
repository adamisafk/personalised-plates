import React, { Component } from 'react'
import UserProfile from './subcomponents/UserProfile'

import authService from '../services/auth.service'
import { Redirect } from 'react-router'

export class Profile extends Component {
    render() {
        if(authService.getToken() === null) {
            return <Redirect to='/login' />
        }
        return (
            <div>
                <UserProfile />
            </div>
        )
    }
}

export default Profile
