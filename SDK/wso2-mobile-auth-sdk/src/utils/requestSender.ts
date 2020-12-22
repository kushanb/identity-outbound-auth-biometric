import { fetch } from 'react-native-ssl-pinning';
import { Alert } from 'react-native';


export class RequestSender { 

    public constructor() { }

    public sendRequest(url: string, requestMethod: any, requestHeaders: any, body: any) { 
        fetch(url, {
            method: requestMethod,
            disableAllSecurity: true,
            sslPinning: {
                certs: ["wso2carbon"], // TODO: make the certificate name configurable
            },
            headers: requestHeaders,
            body: body,
            })
            .then(response => {
            console.log(`response received ${response.bodyString}`)
            Alert.alert(
            "Request",
            "Status: " + response.status + "\nMessage: " + response.bodyString,
            [
                { text: "OK", onPress: () => console.log("OK Pressed") }
            ],
            { cancelable: false }
            );

        })
        .catch(err => {
            console.log(`error: ${err.status}`)
        });
    }
}