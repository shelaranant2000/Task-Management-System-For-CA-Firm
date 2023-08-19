import axios from 'axios';
import React, { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';

const Home = () => {
    const authuser = useSelector((state) => state.auth.user);
    const [tasks, setTasks] = useState([]);
    useEffect(() => {
        console.log("useefeect called");
        setTasks([]);
        myrender();
    }, [authuser]);

    const myrender = () => {
        var temprole=authuser.role;
        temprole=temprole.toLowerCase();
        axios.post(`http://localhost:8080/task/${temprole}/${authuser.id}`).then(
            (response) => {
                setTasks(response.data);
                console.log(response);
            }, (error) => {
                console.log(error);
            }
        )
    }
    

    return (
        <div className='container mt-3'>
            <table className="table">
                <thead>
                    <tr>
                        <th scope="col">Sr No</th>
                        <th scope="col">Title</th>
                        <th scope="col">Description</th>
                        <th scope="col">Status</th>
                        <th scope="col">View Details</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        tasks.map((task, index) => (
                            <tr key={index}>
                                <th scope="row">{index + 1}</th>
                                <td>{task.title}</td>
                                <td>{task.desc}</td>
                                <td>{task.status}</td>

                                <td><button href="#" type='Submit' className="btn btn-secondary" onClick={() => {  }} >View Details</button>
                                </td>

                            </tr>
                        ))
                    }
                </tbody>
            </table>           
        </div>
    )
}
export default Home;