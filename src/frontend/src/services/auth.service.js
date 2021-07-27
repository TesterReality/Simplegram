import axios from 'axios';

const API_URL = 'http://localhost:8080/api/auth/';

class AuthService {
    login(user) {
        return axios
            .post(API_URL + 'signin', {
                login: user.login,
                password: user.password
            })
            .then(response => {
                if (response.data.accessToken) {
                    localStorage.setItem('user', JSON.stringify(response.data));
                }

                return response.data;
            });
    }

    logout() {
        localStorage.removeItem('user');
    }

    register(formdata) {

        console.log(formdata);
        //console.log(formdata.get('file'));
        const data = [...formdata.entries()];
        console.log(data);

        return axios.post(
            API_URL + 'signup',
            formdata,
            {headers: {'Content-Type': 'multipart/form-data'}}
        );
    }
}

export default new AuthService();
