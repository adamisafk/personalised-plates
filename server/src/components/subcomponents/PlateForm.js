import React from 'react'
import { Button, Col, Form, Row } from 'react-bootstrap'

function PlateForm() {
    return (
        <div style={{paddingBottom: '50px'}}>
            <Form>
                <Row>
                    <Col xs={8}>
                        <Form.Group controlId="formRegSearch.TextInput">
                            <Form.Control size="lg" type="text" placeholder="SEARCH" />
                        </Form.Group>
                    </Col>
                    <Col>
                        <Form.Group controlId="formRegSearch.StyleInput">
                            <Form.Control size="lg" as="select">
                                <option>All Styles</option>
                                <option>AB18 ABC</option>
                                <option>A1 ABC</option>
                                <option>ABC 123</option>
                            </Form.Control>
                        </Form.Group>
                    </Col>
                </Row>

                <Row style={{justifyContent: 'center'}}>
                    <Button variant="success" size="lg" type="submit">Search</Button>
                </Row>
            </Form>
        </div>
    )
}

export default PlateForm
