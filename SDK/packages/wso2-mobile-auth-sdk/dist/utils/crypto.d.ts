export declare class Crypto {
    /**
     * Generate new keypair
     *
     * @returns the generated keypair object containing PEM strings
     */
    static generateKeypair(): any;
    /**
     * Signs the challenge and returns the signed string
     *
     * @param prvKey private key to sign the challenge
     * @param challenege string containing the challenge
     *
     * @returns a base64 string of the signed challenege
     */
    static signChallenge(privateKey: string, challenge: string): string;
}
