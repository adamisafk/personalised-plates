import React from 'react'
import { Container } from 'react-bootstrap'
import PlateForm from './subcomponents/PlateForm'
import PlateList from './subcomponents/PlateList'

function Home() {
    return (
        <div>
            <Container>
                <PlateForm />

                <PlateList />
            </Container>
        </div>
    )
}

export default Home
