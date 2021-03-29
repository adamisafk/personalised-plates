import React from 'react'
import { Container, Row, Col } from 'react-bootstrap'

function PlateList() {
    return (
        <div>
            <Container className="text-center">
                <Row className="pb-5">
                    <Col>Plate 1</Col>
                    <Col>Plate 2</Col>
                    <Col>Plate 3</Col>
                    <Col>Plate 4</Col>
                </Row>
                <Row className="pb-5">
                    <Col>Plate 5</Col>
                    <Col>Plate 6</Col>
                    <Col>Plate 7</Col>
                    <Col>Plate 8</Col>
                </Row>
                <Row className="pb-5">
                    <Col>Plate 9</Col>
                    <Col>Plate 10</Col>
                    <Col>Plate 11</Col>
                    <Col>Plate 12</Col>
                </Row>
            </Container>
        </div>
    )
}

export default PlateList
