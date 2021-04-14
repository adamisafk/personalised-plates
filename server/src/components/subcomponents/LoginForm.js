import React, { Component } from 'react'
import {Button, Container, Row, Form} from 'react-bootstrap'
import { Redirect } from 'react-router'

import authService from "../../services/auth.service";

export default class LoginForm extends Component {
    constructor(props) {
        super(props)
        this.state = {
            email: "",
            password: "",
            redirect: false,
            msg: ""
        }
        this.handleLogin = this.handleLogin.bind(this)
        this.onChangeEmail = this.onChangeEmail.bind(this)
        this.onChangePassword = this.onChangePassword.bind(this)
    }
    componentDidMount(props) {
        if(this.props.location.state) {
            this.setState({msg: this.props.location.state.msg})
        }
    }

    onChangeEmail(e) {
        this.setState({
            email: e.target.value
        })
    }
    onChangePassword(e) {
        this.setState({
            password: e.target.value
        })
    }
    handleLogin(e) {
        e.preventDefault()
        authService.login(this.state.email, this.state.password).then(
            () => {
                this.setState({redirect: true})
                window.location.reload()
            }
        )
    }

    render() {
        const redirect = this.state.redirect
        if(redirect) {
            return <Redirect to='/profile' />
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
                                    <Form.Control type="email" placeholder="Enter email" value={this.state.email} onChange={this.onChangeEmail} />
                                </Form.Group>
                                <Form.Group>
                                    <Form.Label>Password</Form.Label>
                                    <Form.Control type="password" placeholder="Enter password" value={this.state.password} onChange={this.onChangePassword} />
                                </Form.Group>
                                <Form.Group>
                                    <Button onClick={this.handleLogin}>Login</Button>
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
