import { fetch } from 'react-native-ssl-pinning';
import { Alert } from 'react-native';
export class RequestSender {
    constructor() { }
    sendRequest(url, requestMethod, requestHeaders, body) {
        fetch(url, {
            method: requestMethod,
            disableAllSecurity: true,
            sslPinning: {
                certs: ["wso2carbon"],
            },
            headers: requestHeaders,
            body: body,
        })
            .then(response => {
            console.log(`response received ${response.bodyString}`);
            Alert.alert("Request", "Status: " + response.status + "\nMessage: " + response.bodyString, [
                { text: "OK", onPress: () => console.log("OK Pressed") }
            ], { cancelable: false });
        })
            .catch(err => {
            console.log(`error: ${err.status}`);
        });
    }
}
