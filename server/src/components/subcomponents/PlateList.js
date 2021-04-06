import React, { Component } from 'react'
import { Col, Container, Row } from 'react-bootstrap';

export class PlateList extends Component {
    constructor(props) {
        super(props)
        this.state = {plates: []}
    }
    componentDidMount() {
        this.getAllPlates()
    }
    
    getAllPlates() {
        fetch('http://service:8080/plate/all')
            .then(res => console.log(res))
    }

    render() {
        const plates = this.state.plates.map((plate, i) => (
            <Col>{plate.reg}</Col>
        ))

        return (
            <div>
                <Container className="text-center">
                    <Row className="pb-5">
                        {plates}
                    </Row>
                </Container>
            </div>
        )
    }
}

export default PlateList
