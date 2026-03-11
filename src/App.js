import { useEffect, useState } from "react";
import axios from "axios";

function App() {

  const [countries, setCountries] = useState([]);
  const [search, setSearch] = useState("");
  const [selectedCountry, setSelectedCountry] = useState(null);

  useEffect(() => {
    fetchCountries();
  }, []);

  const fetchCountries = async () => {
    const response = await axios.get("http://localhost:8080/api/countries");
    setCountries(response.data);
  };

  const filteredCountries = countries.filter((country) =>
      country.name.toLowerCase().includes(search.toLowerCase())
  );

  return (
      <div style={{padding:"20px"}}>

        <h2>Countries List</h2>

        <input
            type="text"
            placeholder="Search country..."
            value={search}
            onChange={(e) => setSearch(e.target.value)}
        />

        <table border="1" style={{marginTop:"20px",width:"100%"}}>
          <thead>
          <tr>
            <th>Flag</th>
            <th>Name</th>
            <th>Capital</th>
            <th>Region</th>
            <th>Population</th>
          </tr>
          </thead>

          <tbody>
          {filteredCountries.map((country, index) => (
              <tr key={index} onClick={() => setSelectedCountry(country)}>
                <td>
                  <img src={country.flag} width="40" />
                </td>
                <td>{country.name}</td>
                <td>{country.capital}</td>
                <td>{country.region}</td>
                <td>{country.population}</td>
              </tr>
          ))}
          </tbody>
        </table>

        {selectedCountry && (
            <div style={{
              position:"fixed",
              top:"30%",
              left:"40%",
              background:"white",
              padding:"20px",
              border:"1px solid black"
            }}>
              <h3>{selectedCountry.name}</h3>

              <img src={selectedCountry.flag} width="100"/>

              <p>Capital: {selectedCountry.capital}</p>
              <p>Region: {selectedCountry.region}</p>
              <p>Population: {selectedCountry.population}</p>

              <button onClick={() => setSelectedCountry(null)}>
                Close
              </button>

            </div>
        )}

      </div>
  );
}

export default App;