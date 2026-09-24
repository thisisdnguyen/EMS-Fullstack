import { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import { register } from '../services/AuthService'

const RegisterComponent = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [successMessage, setSuccessMessage] = useState('');
    const navigate = useNavigate();

    function handleRegister(e) {
        e.preventDefault();
        setErrorMessage('');
        setSuccessMessage('');

        register(username, password)
            .then(() => {
                setSuccessMessage('Sign up successfully! Navigating to login page');
                setTimeout(() => navigate('/login'), 1500);
            })
            .catch((error) => {
                if (error.response && error.response.status === 409) {
                    setErrorMessage('Username existed');
                } else {
                    setErrorMessage('Failed to register, please try again later');
                }
            });
    }

    return (
        <div className="container">
            <br />
            <div className="row">
                <div className="card col-md-6 offset-md-3 offset-md-3">
                    <h2 className="text-center">Sign Up</h2>
                    <div className="card-body">
                        <form onSubmit={handleRegister}>
                            <div className="form-group mb-2">
                                <label className="form-label">Username</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    value={username}
                                    onChange={(e) => setUsername(e.target.value)}
                                />
                            </div>
                            <div className="form-group mb-2">
                                <label className="form-label">Password</label>
                                <input
                                    type="password"
                                    className="form-control"
                                    value={password}
                                    onChange={(e) => setPassword(e.target.value)}
                                />
                            </div>
                            {errorMessage && (
                                <p className="text-danger">{errorMessage}</p>
                            )}
                            {successMessage && (
                                <p className="text-success">{successMessage}</p>
                            )}
                            <button className="btn btn-primary" type="submit">Sign Up</button>
                        </form>
                        <p className="mt-2">
                            Already have an account? <Link to="/login">Login</Link>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default RegisterComponent