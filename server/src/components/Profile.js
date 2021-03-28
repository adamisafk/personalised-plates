import React from 'react'
import { Container, Row } from 'react-bootstrap'

function Profile() {
    return (
        <div>
            <Container>
                <Row>
                    <h1>Your Profile</h1> 
                </Row>


                <Row>
                    <h5>Personal Information</h5>
                </Row>
                <Row>
                    <p>Blah blah blah</p>
                </Row>

                <Row>
                    <h5>Your Plates</h5>
                </Row>
                <Row>
                    <p>Blah blah blah</p>
                </Row>
            </Container>
        </div>
    )
}

export default Profile
