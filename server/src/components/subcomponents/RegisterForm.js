import React, { Component } from 'react'
import {Button, Container, Row, Form, Spinner} from 'react-bootstrap'
import { Redirect } from 'react-router'

import authService from "../../services/auth.service";

export default class RegisterForm extends Component {
    constructor(props) {
        super(props)
        this.state = {
            email: "",
            password: "",
            passConf: "",
            firstName: "",
            lastName: "",
            redirect: false,
            loading: false
        }
        this.onChange = this.onChange.bind(this)
        this.handleRegister = this.handleRegister.bind(this)
    }
    onChange(e) {
        this.setState({[e.target.name]: e.target.value})
    }
    
    handleRegister(e) {
        e.preventDefault()
        this.setState({loading: true})
        authService.register(this.state.firstName, this.state.lastName, this.state.email, this.state.password).then(
            () => {
                this.setState({redirect: true, loading: false})
                window.location.reload()
            }
        )
    }

    render() {
        const redirect = this.state.redirect
        if(redirect) {
            return <Redirect to='/login' />
        }
        const renderSpinner = () => {
            // if loading state variable is true, render spinner in button
            if(this.state.loading) {
                return (
                    <Spinner
                        as="span"
                        animation="border"
                        size="sm"
                        role="status"
                        aria-hidden="true"
                    />
                )
            }
            return "Register"
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
                                    <Form.Control name="email" type="email" placeholder="Enter email" value={this.state.email} onChange={this.onChange} />
                                </Form.Group>
                                <Form.Group>
                                    <Form.Label>Password</Form.Label>
                                    <Form.Control name="password" type="password" placeholder="Enter password" value={this.state.password} onChange={this.onChange} />
                                </Form.Group>
                                <Form.Group>
                                    <Form.Label>Confirm Password</Form.Label>
                                    <Form.Control name="passConf" type="password" placeholder="Re-Enter password" value={this.state.passConf} onChange={this.onChange} />
                                </Form.Group>
                                <Form.Group>
                                    <Form.Label>First Name</Form.Label>
                                    <Form.Control name="firstName" type="text" placeholder="Enter first name" value={this.state.firstName} onChange={this.onChange} />
                                </Form.Group>
                                <Form.Group>
                                    <Form.Label>Last Name</Form.Label>
                                    <Form.Control name="lastName" type="text" placeholder="Enter last name" value={this.state.lastName} onChange={this.onChange} />
                                </Form.Group>
                                <Form.Group>
                                    <Button onClick={this.handleRegister}>
                                        {renderSpinner()}
                                    </Button>
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
