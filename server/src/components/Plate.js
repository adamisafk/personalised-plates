import React, { Component } from 'react'
import axios from 'axios'
import { Button, Card, Container, Form, Row } from 'react-bootstrap'
import Payment from './Payment'

export class Plate extends Component {
    constructor(props) {
        super(props)
        this.state = {plate: [], check: false, errorMsg: ''}

        this.checkboxOnChange = this.checkboxOnChange.bind(this)
        this.loadPayment = this.loadPayment.bind(this)
    }
    componentDidMount() {
        this.getPlate()
    }

    getPlate() {
        // Get ID Parameter
        var id = this.props.id.match.params.id
        // Call API with ID
        axios.get('http://localhost:9300/plate/' + id)
            //.then(response => this.setState({ plate: response.data }))
            .then(response => this.setState({ plate: response.data }))
    }
    checkboxOnChange() {
        if(this.state.check === false) {
            this.setState({check: true})
        } else {
            this.setState({check: false})
        }
    }
    loadPayment() {
        if(this.state.check) {
            // Clear error message and load payment component
            this.setState({errorMsg: ''})
            return <Payment />
        } else {
            // Display error message
            this.setState({errorMsg: 'Please confirm that you understand the age of vehicle restriction.'})
        }
    }

    render() {
        return (
            <div>
                <Container>
                    <Row style={{justifyContent: 'center'}}>
                        <h1>Buy your Personalised Registration</h1>
                    </Row>
                    <Row style={{justifyContent: 'center', padding: "10px"}}>
                        <Card style={{width: '18', textAlign: 'center'}} bg="warning">
                            <Card.Body>
                                <Card.Title style={{color: 'black', fontSize: "80px"}}>{this.state.plate.reg}</Card.Title>
                            </Card.Body>
                        </Card> 
                    </Row>
                    <Row style={{justifyContent: 'center'}}>
                        <h3>Your chosen plate: £{this.state.plate.price}</h3>
                    </Row>
                    <Row style={{justifyContent: 'center'}}>
                        <p>Our price includes VAT and the DVLA £80 assignment fee.</p>
                    </Row>

                    <Row style={{justifyContent: 'center'}}>
                        <Card style={{width: '50rem', height: '20rem', textAlign: 'left'}} bg="dark">
                            <Card.Body>
                                <Card.Text><b>Please Note:</b> This registration can only be assigned to a vehicle which was registered as new on or after the specified date. You cannot use a personalised registration to make a vehicle look newer than it is.</Card.Text>
                                <Form>
                                    <Form.Group controlId="dateConfirmation">
                                        <Form.Check onChange={this.checkboxOnChange} type="checkbox" label="Yes, I understand the age of vehicle restriction." />
                                    </Form.Group>
                                </Form>

                                <Card className="px-5 pb-10" style={{textAlign: 'left'}} bg="danger">
                                    <Card.Title className="pt-3"><h6>Postal Service Delays</h6></Card.Title>
                                    <Card.Text className="pb-3">Following your purchase, you will receive a confirmation email as proof of purchase. However, please be aware that due to the current high demand on postal services across the UK you may experience a delay in receiving the documentation needed in order to assign the registration to a vehicle.</Card.Text>
                                </Card>
                            </Card.Body>
                        </Card>
                    </Row>
                    <Row style={{justifyContent: 'center', paddingTop: '10px'}}>
                        <p style={{color: 'red'}}>{this.state.errorMsg}</p>
                    </Row>
                    <Row style={{justifyContent: 'center'}}>
                        <Button onClick={this.loadPayment} variant="success" size="lg">Buy Now</Button>
                    </Row>
                </Container>
            </div>
        )
    }
}

export default Plate