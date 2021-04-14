import React, { Component } from 'react'
import { Button, Container, Row } from 'react-bootstrap'
import { Redirect } from 'react-router'

import authService from '../services/auth.service'
import userService from '../services/user.service'
import authHeader from '../services/auth-header'
import axios from 'axios'

export class Payment extends Component {
    constructor(props) {
        super(props)
        this.state = {
            plateId: 0
        }
    }
    componentDidMount(props) {
        this.setState({plateId: this.props.location.state.plateId})
        this.makePayment(this.props.location.state.plateId)
    }

    makePayment(plateId) {
        userService.createOrder(plateId)
            .then(response => {
                return <Redirect to="/profile" />
            })
    }

    render() {
        if(authService.getToken() === null) {
            return <Redirect to={{
                pathname: "/login",
                state: {msg: "You must login first"}
            }} />
        }
        return (
            <div>
                <Container className="text-center">
                    <Row style={{justifyContent: 'center'}}>
                        <h1>Payment of {this.state.plateId}</h1>
                    </Row>
                    <Row style={{justifyContent: 'center'}}>
                        <h3>Payment portal goes here</h3>
                    </Row>
                    <Row style={{justifyContent: 'center'}}>
                        
                    </Row>
                </Container>
            </div>
        )
    }
}

export default Payment
