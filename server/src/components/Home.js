import React from 'react'
import { Container, Row, Col } from 'react-bootstrap'
import PlateForm from './subcomponents/PlateForm'

function Home() {
    return (
        <div>
            <Container>
                <PlateForm/ >

                <Row>
                    <Col>Plate 1</Col>
                    <Col>Plate 2</Col>
                    <Col>Plate 3</Col>
                    <Col>Plate 4</Col>
                </Row>
            </Container>
        </div>
    )
}

export default Home
