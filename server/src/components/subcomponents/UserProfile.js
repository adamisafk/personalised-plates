import React, { Component } from 'react'
import { Col, Container, Row } from 'react-bootstrap'

import userService from '../../services/user.service'

export default class UserProfile extends Component {
    constructor(props) {
        super(props)
        this.state = {
            userDetails: {}
        }
    }
    componentDidMount() {
        this.getUser()
    }
    getUser() {
        userService.getUser()
            .then(response => {this.setState({userDetails: response.data})})
    }
    render() {
        return (
            <div>
                <Container className="text-center">
                    <Row>
                        <Col>
                            <h1>Profile</h1>
                        </Col>
                    </Row>
                    <hr style={{backgroundColor: 'white'}} /><br/>
                    <Row>
                        <Col>
                            <h3>Your Details</h3><br />
                        </Col>
                    </Row>
                    <Row className="justify-content-md-center">
                        <Col xs lg="2">
                            <p>Email:</p>
                            <p>First Name:</p>
                            <p>Last Name:</p>
                        </Col>
                        <Col xs lg="2">
                            <p>{this.state.userDetails.email}</p>
                            <p>{this.state.userDetails.firstName}</p>
                            <p>{this.state.userDetails.lastName}</p>
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <h3>Your Plates</h3><br />
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <h3>Your Orders</h3><br />
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            
                        </Col>
                    </Row>
                </Container>
            </div>
        )
    }
}
