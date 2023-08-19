import React, { useState, useEffect } from 'react'
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

export const Getallclients = () => {
    const [client, setClient] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        axios.get("http://localhost:8080/client").then((response) => {
            setClient(response.data);
        }, (error) => {
            console.log(error)
        });

    }, []);

    const handleClick = (cliobj) => {
        console.log(cliobj.clientName);
        navigate(`/createclient`, { state: { cliobj } });
    }

    const handledeleteClick = async (cliobj) => {
        const confirmDelete = window.confirm(`Are you sure to delete this Client with Id : ${cliobj.clientId}`);
        if (confirmDelete) {
            await axios.delete(`http://localhost:8080/client/${cliobj.clientId}`).then(
                (response) => {
                    if (response.data === "Document Deleted") {
                        alert("Client Deleted Successfully");
                    } else {
                        alert(response.data);
                    }
                })
        }
    }
    return (
        <div className="container mt-3">
            <h2 className='text-center'>Clients</h2>
            <table className="table table-striped table-bordered table-hover" style={{"border":"2px solid skyblue","backgroundColor":"#C9E5FF"}}>
                <thead>
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Name</th>
                        <th scope="col">Contact</th>
                        <th scope="col">Email</th>
                        <th scope="col">Address</th>
                        <th scope="col" className='text-center'>Update</th>
                        <th scope="col" className='text-center'>Delete</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        client.map((cliobj,index) => (
                            <tr key={index}>
                                <th scope="row">{cliobj.clientId}</th>
                                <td>{cliobj.clientName}</td>
                                <td>{cliobj.clientContact}</td>
                                <td>{cliobj.clientEmail}</td>
                                <td>{cliobj.clientAddress}</td>                                
                                <td className='text-center'><a className="btn btn-info" onClick={() => handleClick(cliobj)}>Update</a></td>
                                <td className='text-center'><a href="" className="btn btn-info" onClick={() => handledeleteClick(cliobj)} >Delete</a></td>                                
                            </tr>
                        ))
                    }
                </tbody>
            </table>
        </div>
    )
}
export default Getallclients;