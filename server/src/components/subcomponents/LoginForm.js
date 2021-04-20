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
            msg: "",
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
        this.setState({ [e.target.name]: e.target.value })
    }
    handleLogin(e) {
        e.preventDefault()
        this.setState({loading: true})
        authService.login(this.state.email, this.state.password).then(
            () => {
                this.setState({redirect: true, loading: false})
                window.location.reload()
            }
        )
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

        return (
            <div>
                <Container className="text-center">
                    <p>{this.state.msg}</p>
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
