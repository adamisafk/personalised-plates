import React, { Component } from 'react'
import { Button, Card, CardColumns, Container } from 'react-bootstrap';
import axios from 'axios'

export class PlateList extends Component {
    constructor(props) {
        super(props)
        this.state = {
            style: 0, //default style to 'All Styles'
            plates: []
        }
    }
    componentDidMount() {
        // If a search query exists, call getPlateByQuery method
        if(this.props.query[0]) {
            this.getPlateByQuery()
        }
    }
    
    // Calls API to get list of plates and sets the response payload in state
    getPlateByQuery() {
        if(this.props.query[1] === 0) {
            // If style selected is 'All Styles', call api with search query without style as parameter
            axios.get('http://localhost:9300/plate/find/' + this.props.query[0])
                .then(response => this.setState({ plates: response.data }))
        } else {
            // If style selected is anything other than 'All Styles', call api with both search query and style
            axios.get('http://localhost:9300/plate/find/' + this.props.query[0] + '/' + this.props.query[1])
                .then(response => this.setState({ plates: response.data }))
        }
    }

    render() {
        // Map plates results as a card
        const plates = this.state.plates.map((plate, i) => (
            <Card style={{width: '18rem'}} bg="warning" key={plate.id}>
                <Card.Body>
                    <Card.Title style={{color: 'black'}}>{plate.reg}</Card.Title>
                    <Card.Subtitle className="mb-2 text-muted">{plate.allocated === true ? "Unavailable" : "Available"}</Card.Subtitle>
                    <Card.Subtitle className="mb-2 text-muted">£{plate.price}</Card.Subtitle>
                    <Button variant="success" href={"/plate/" + plate.id}>View</Button>
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
