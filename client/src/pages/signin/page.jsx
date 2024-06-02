import React, { useState } from 'react';
import { Link } from 'react-router-dom'; // Import Link from react-router-dom
import Styles from './page.module.scss';

const Signin = () => {
    const [formData, setFormData] = useState({
        email: '',
        password: '',
    });

    const [error, setError] = useState('');

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await fetch('API_ENDPOINT_URL', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData),
            });

            if (!response.ok) {
                const errorMessage = await response.text();
                throw new Error(errorMessage);
            }

            // Handle success response
        } catch (error) {
            setError(error.message);
        }
    };

    return (
        <div className={Styles.container}>
            <div className={Styles.welcome__Section}>
                <h1 className={Styles.welcome__text}>Welcome to Hospital Stocks!</h1>
                <p className={Styles.welcome__subtext}>Glad to see you back!</p>
            </div>
            <div className={Styles.signin__container}>
                <h1 className={Styles.signin__title}>Sign In</h1>
                {error && <div className={Styles.alert}>{error}</div>}
                <form className={Styles.signin__form} onSubmit={handleSubmit}>
                    <label className={Styles.form__label} htmlFor="email">Your Email:</label><br />
                    <input
                        className={Styles.form__input}
                        type="text"
                        id="email"
                        name="email"
                        placeholder="Enter the email"
                        value={formData.email}
                        onChange={handleChange}
                        required
                    /><br />
                    <label className={Styles.form__label} htmlFor="password">Your Password:</label><br />
                    <input
                        className={Styles.form__input}
                        type="password"
                        id="password"
                        name="password"
                        placeholder="Enter the password"
                        value={formData.password}
                        onChange={handleChange}
                        required
                    />
                    <div className={Styles.submit__button}>
                        <input className={Styles.button__input} name="login" type="submit" value="Sign In" />
                    </div>
                </form>
                <div className={Styles.signin__line}></div>
                <div className={Styles.not__signedup}>
                    <div className={Styles.not__signupQuestion}>Don't have an account?&nbsp;&nbsp;</div>

                    <Link className={Styles.link__signup} to="../signup">Sign Up!</Link>
                </div>
            </div>
        </div>
    );
};

export default Signin;
