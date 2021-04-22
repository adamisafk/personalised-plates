import React, { Component } from 'react'
import {Button, Container, Row, Form, Spinner} from 'react-bootstrap'
import { Redirect } from 'react-router'

import authService from "../../services/auth.service";

export default class LoginForm extends Component {
    constructor(props) {
        super(props)
        this.state = {
            email: "",
            password: "",
            redirect: false,
            msg: [],
            loading: false
        }
        this.handleLogin = this.handleLogin.bind(this)
        this.onChange = this.onChange.bind(this)
    }
    componentDidMount(props) {
        if(this.props.location.state) {
            this.setState({msg: this.props.location.state.msg})
        }
    }

    onChange(e) {
        e.preventDefault()
        this.setState({ [e.target.name]: e.target.value })
    }
    handleLogin(e) {
        e.preventDefault()
        // Reset error messages
        this.setState({msg: []})
        let errorMsg = []
        const regex = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/i; // Regex to check if email format is valid

        // Run validation checks, pushing appropriate messages to array if caught
        if(!this.state.email) {
            errorMsg.push("Email cannot be empty!")
        } else if(!regex.test(this.state.email)) {
            errorMsg.push("Email is invalid!")
        }
        if(!this.state.password) {
            errorMsg.push("Password cannot be empty!")
        } else if(this.state.password.length < 6) {
            errorMsg.push("Password must be more than 6 characters!")
        }

        // Set error messages to state
        this.setState({msg: errorMsg})

        // If error messages are empty, attempt login
        if(!errorMsg[0]) {
           this.setState({loading: true})
            authService.login(this.state.email, this.state.password).then(
                () => {
                    this.setState({redirect: true, loading: false})
                    window.location.reload()
                }
            ).catch(function(error) {
                if(error.response) {
                    this.setState({msg: ["Email and/or password is incorrect!"], loading: false})
                }
            })
        }
    }

    render() {
        const redirect = this.state.redirect
        if(redirect) {
            return <Redirect to='/profile' />
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
            return "Login"
        }
        const errorMessages = this.state.msg.map(message => {
            return <p>{message}</p>
        })

        return (
            <div>
                <Container className="text-center">
                    {errorMessages}
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
                                    <Button onClick={this.handleLogin}>{renderSpinner()}</Button>
                                </Form.Group>
                            </Form>
                        </div>
                    </Row>


                    <Row style={{justifyContent: 'center', paddingTop: '3rem'}}>
                        <Button href="/register">No Account? Register!</Button>
                    </Row>
                </Container>
            </div>
        )
    }
}
