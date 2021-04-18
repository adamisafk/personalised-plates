import React, { Component } from 'react'
import { Button, Card, CardColumns, Col, Container, Row, Table } from 'react-bootstrap'

import userService from '../../services/user.service'

export default class UserProfile extends Component {
    constructor(props) {
        super(props)
        this.state = {
            userDetails: {},
            orders: [],
            plates: {}
        }
    }
    componentDidMount() {
        // Get user details
        userService.getUser()
            .then(response => {this.setState({userDetails: response.data})})
        // Get orders associated with user
        userService.getOrders()
            .then(response => {this.setState({orders: response.data})})
    }
    formatDate(dateStr) {
        return new Date(dateStr).toISOString().replace(/T/, ' ').replace(/\..+/, '')
    }

    refundOnClick(orderId, plateId) {
        userService.createRefundOrder(orderId, plateId)
            .then(response => console.log(response.data))
        window.location.reload()
    }
    transferOnClick(orderId) {
        // Input email of new customer
        var email = prompt("Please enter the email address of the new owner: ")
        if(email != null) {
           userService.createTransferOrder(email, orderId)
            .then(response => console.log(response.data)) 
        }
    }
    sellOnClick(orderId, plateId) {
        let price = prompt("Please enter the price you want to sell at: ")
        if(price != null) {
            userService.createSellOrder(orderId, plateId, price)
            .then(response => {
                if(response.data === "Failed to parse double") {
                    alert("Incorrect formatting for price. Please type in decimal without the £, for example: 23.12")
                }
            })
        }
    }

    render() {
        const orders = [...this.state.orders].reverse().map((order, i) => {
            var status
            switch(order.status) {
                case 1:
                    status = "Processing"
                    break
                case 2:
                    status = "Processed"
                    break
                case 3:
                    status = "Refunded"
                    break
                case 4:
                    status = "On Sale"
                    break
                case 5:
                    status = "Sold"
                    break
                case 6:
                    status = "Transferred"
                    break
                default:
                    status = "None"
            }
            return (
                <tr>
                    <td>{order.id}</td>
                    <td>{order.plate.reg}</td>
                    <td>{this.formatDate(order.date)}</td>
                    <td>{status}</td>
                </tr>
            )
        })
        const plates = this.state.orders.map((order, i) => {
            if(order.plate.allocated === true && order.null === false) {
                return (
                    <Card style={{width: '18rem'}} bg="warning" key={order.plate.id}>
                        <Card.Body>
                            <Card.Title style={{color: 'black'}}>{order.plate.reg}</Card.Title>
                            <Button onClick={() => this.refundOnClick(order.id, order.plate.id)} className="mr-1" variant="success">Refund</Button>
                            <Button onClick={() => this.sellOnClick(order.id, order.plate.id)} className="mr-1" variant="success">Sell</Button>
                            <Button onClick={() => this.transferOnClick(order.id)} className="mr-1" variant="success">Transfer</Button>
                        </Card.Body>
                    </Card>
                )
            }
            return null;
        })
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
                    <Row className="pb-5 justify-content-md-center">
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
                    <hr style={{backgroundColor: 'white'}} /><br/>
                    <Row>
                        <Col>
                            <h3>Your Plates</h3><br />
                        </Col>
                    </Row>
                    <Row className="pb-5">
                        <Col>
                            <CardColumns>
                                {plates}
                            </CardColumns>
                        </Col>
                    </Row>
                    <hr style={{backgroundColor: 'white'}} /><br/>
                    <Row>
                        <Col>
                            <h3>Your Orders</h3><br />
                        </Col>
                    </Row>
                    <Row className="pb-5">
                        <Col>
                            <Table variant="dark" striped bordered hover size="sm">
                                <thead>
                                    <tr>
                                    <th>#</th>
                                    <th>Registration</th>
                                    <th>Date and Time</th>
                                    <th>Status</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {orders}
                                </tbody>
                            </Table>
                        </Col>
                    </Row>
                </Container>
            </div>
        )
    }
}
