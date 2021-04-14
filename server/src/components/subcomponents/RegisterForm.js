import React, { Component } from 'react'
import {Button, Container, Row, Form} from 'react-bootstrap'
import { Redirect } from 'react-router'

import authService from "../../services/auth.service";

export default class RegisterForm extends Component {
    constructor(props) {
        super(props)
        this.state = {
            email: "",
            password: "",
            firstName: "",
            lastName: "",
            redirect: false
        }
        this.handleRegister = this.handleRegister.bind(this)
        this.onChangeEmail = this.onChangeEmail.bind(this)
        this.onChangePassword = this.onChangePassword.bind(this)
        this.onChangeFirstName = this.onChangeFirstName.bind(this)
        this.onChangeLastName = this.onChangeLastName.bind(this)
    }
    onChangeEmail(e) {
        this.setState({email: e.target.value})
    }
    onChangePassword(e) {
        this.setState({password: e.target.value})
    }
    onChangeFirstName(e) {
        this.setState({firstName: e.target.value})
    }
    onChangeLastName(e) {
        this.setState({lastName: e.target.value})
    }
    
    handleRegister(e) {
        e.preventDefault()
        authService.register(this.state.firstName, this.state.lastName, this.state.email, this.state.password).then(
            () => {
                this.setState({redirect: true})
                window.location.reload()
            }
        )
    }

    render() {
        const redirect = this.state.redirect
        if(redirect) {
            return <Redirect to='/login' />
        }
        return (
            <div>
                <Container className="text-center">
                    <p></p>
                    <Row style={{justifyContent: 'center'}}>
                        <div className="card card-container bg-dark">
                            <Form style={{padding: '5rem', width: '30rem'}}>
                                <Form.Group>
                                    <Form.Label>Email</Form.Label>
                                    <Form.Control type="email" placeholder="Enter email" value={this.state.email} onChange={this.onChangeEmail} />
                                </Form.Group>
                                <Form.Group>
                                    <Form.Label>Password</Form.Label>
                                    <Form.Control type="password" placeholder="Enter password" value={this.state.password} onChange={this.onChangePassword} />
                                </Form.Group>
                                <Form.Group>
                                    <Form.Label>First Name</Form.Label>
                                    <Form.Control type="text" placeholder="Enter first name" value={this.state.firstName} onChange={this.onChangeFirstName} />
                                </Form.Group>
                                <Form.Group>
                                    <Form.Label>Last Name</Form.Label>
                                    <Form.Control type="text" placeholder="Enter last name" value={this.state.lastName} onChange={this.onChangeLastName} />
                                </Form.Group>
                                <Form.Group>
                                    <Button onClick={this.handleRegister}>Register</Button>
                                </Form.Group>
                            </Form>
                        </div>
                    </Row>


                    <Row style={{justifyContent: 'center', paddingTop: '3rem'}}>
                        <Button href="/login">Got An Account? Login!</Button>
                    </Row>
                </Container>
            </div>
        )
    }
}
