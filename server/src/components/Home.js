import React from 'react'
import { Container } from 'react-bootstrap'
import PlateForm from './subcomponents/PlateForm'
import PlateList from './subcomponents/PlateList'
import swearjar from 'swearjar'

const { search } = window.location
const query = new URLSearchParams(search).get('search')
const style = new URLSearchParams(search).get('style')

function Home() {
    const loadPlateList = (query, style) => {
        if(query && style) {
            // Profanity checker
            if(!swearjar.profane(query)) {
                // Query does not contain profanity
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
                // Return list component and pass the query and style
                const payload = [query, style]
                return <PlateList query={payload} /> 
            }
            // Query contains profanity
            return <h4>Please refrain from using profanity. If this is a mistake please contact support.</h4>
        }
    }
    return (
        <div>
            <Container className="text-center">
                <PlateForm searchText={query} styleOption={style} />

                {loadPlateList(query, style)}
            </Container>
        </div>
    )
}

export default Home
