import React from 'react'
import { Container } from 'react-bootstrap'
import PlateForm from './subcomponents/PlateForm'
import PlateList from './subcomponents/PlateList'

const { search } = window.location
const query = new URLSearchParams(search).get('search')
const style = new URLSearchParams(search).get('style')

function Home() {
    const loadPlateList = (query, style) => {
        if(query && style) {
            // Assign integer value to style corresponding to database design
            switch(style) {
                case "AB18 ABC":
                    style = 1
                    break
                case "A1 ABC":
                    style = 2
                    break
                case "ABC 123":
                    style = 3
                    break
                default:
                    // aka "All Styles"
                    style = 0
            }
            const payload = [query, style]
            return <PlateList query={payload} />
        }
    }
    return (
        <div>
            <Container>
                <PlateForm searchText={query} />

                {loadPlateList(query, style)}
            </Container>
        </div>
    )
}

export default Home
