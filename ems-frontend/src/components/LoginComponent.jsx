import { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import { login, saveToken } from '../services/AuthService'

const LoginComponent = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const navigate = useNavigate();

    function handleLogin(e) {
        e.preventDefault();
        setErrorMessage('');

        login(username, password)
            .then((response) => {
                saveToken(response.data.token);
                navigate('/employees');
            })
            .catch((error) => {
                setErrorMessage('Invalid Username or Password');
            });
    }

    return (
        <div className="container">
            <br />
            <div className="row">
                <div className="card col-md-6 offset-md-3 offset-md-3">
                    <h2 className="text-center">Login</h2>
                    <div className="card-body">
                        <form onSubmit={handleLogin}>
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
                            <button className="btn btn-primary" type="submit">Login</button>
                        </form>
                        <p className="mt-2">
                            Don't have an account? <Link to="/register">Sign up</Link>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default LoginComponent