import React, { Component } from 'react'
import { Button, Card, CardColumns, Container } from 'react-bootstrap';
import axios from 'axios'

export class PlateList extends Component {
    constructor(props) {
        super(props)
        this.state = {plates: []}
    }
    componentDidMount() {
        this.getAllPlates()
    }
    
    getAllPlates() {
        axios.get('http://localhost:9300/plate/all')
            .then(response => this.setState({ plates: response.data }))
    }

    render() {
        const plates = this.state.plates.map((plate, i) => (
            <Card style={{width: '18rem'}} bg="warning" key={plate.id}>
                <Card.Body>
                    <Card.Title style={{color: 'black'}}>{plate.reg}</Card.Title>
                    <Card.Subtitle className="mb-2 text-muted">£{plate.price}</Card.Subtitle>
                    <Button variant="success" href={"/plate/" + plate.id}>Buy</Button>
                </Card.Body>
            </Card>
        ))

        return (
            <div>
                <Container className="text-center">
                    <CardColumns>
                        {plates}
                    </CardColumns>
                </Container>
            </div>
        )
    }
}

export default PlateList
