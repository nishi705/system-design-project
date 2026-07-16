how to start keycloak application

use cmd as an administration inside keycloak bin folder and paste below command
C:\keycloak-26.7.0\keycloak-26.7.0\bin>kc.bat start-dev

2.http://localhost:8080/realms/nishi-realm/protocol/openid-connect/certs
We trigger above Url to get Keycloak's Public Keys.
and that response is the key set in JWK (JSON Web Key) format.

{
"keys": [
{
"kid": "fNH68oCB9eo7tQXsRiKXsq1hzoiNrnWjcOIKg8ztMdA",
"kty": "RSA",
"alg": "RSA-OAEP",
"use": "enc",
"x5c": [
"MIICpTCCAY0CBgGfaTbgmjANBgkqhkiG9w0BAQsFADAWMRQwEgYDVQQDDAtuaXNoaS1yZWFsbTAeFw0yNjA3MTYwNDM1MzFaFw0zNjA3MTYwNDM3MTFaMBYxFDASBgNVBAMMC25pc2hpLXJlYWxtMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoegoxzquDN+LeD9wb0jsoZXDsIgjI9xiqdPGEIm3k7ncjSuV8TGNDR5xHkPv6vXDxUZ0DxRPdZ7OA/0x2Ibu59+Aspf14WW77D0OybXFgBrvvTHYO2CsSXiozyKJ5q6AZSGoqG83SCmt0DVTLDRTr9XYKzwN6kUFk1gysi7WBiP//IxqgI79zbtIetex1zjs9/Semgfc8oYLoZAghkrwtwO3K6VZRQXRCBOQwabYx4NKbQjuGnmC/pYggfHc2C6Q0VkmOGKOZD704S+1DdN/cthccumWQTw5Gv//xajwMOEc1Lzwmj8NfyjRFsBdj/g+J59yRnnmYe8ifnW41YJ6HwIDAQABMA0GCSqGSIb3DQEBCwUAA4IBAQAvKfdGvPjylN6kSbCHyLOSp6SBE2RSPtrq3ELGi69PyYEoLlj3m+3Yye21H9In9QHwujuN/iRttkScrsTcJd8fTiWI7Vlq+Vxke9bhE9w31wmpu9ztkiQceycNm8ZaqEQxRd+32zHaaheVhs3XCywox+c2hq2MCAea5FE1LFKFJ2OTd/nwI5kmHvGGF4liXWgXcj952T0aB5O9VJrbRYC9I/O99HLuNrhcuLzW3Rjuap4UUlH4OvMydqHD64LjoKNmGLTwnjyBZToDKQRg+YxTodYDBXhgjEjdwrfnUuPLoAUD9eUi1YRVl13xpdf2T3lPyYoLkUhG1+aE5AQFfn96"
],
"x5t": "r8S_UTdp7d5qCdRB05XlpiPZnzQ",
"x5t#S256": "ZA4fKtmMwH2yr1P-AF8MXU6g9KwVMhaKeUArnXdtAk4",
"n": "oegoxzquDN-LeD9wb0jsoZXDsIgjI9xiqdPGEIm3k7ncjSuV8TGNDR5xHkPv6vXDxUZ0DxRPdZ7OA_0x2Ibu59-Aspf14WW77D0OybXFgBrvvTHYO2CsSXiozyKJ5q6AZSGoqG83SCmt0DVTLDRTr9XYKzwN6kUFk1gysi7WBiP__IxqgI79zbtIetex1zjs9_Semgfc8oYLoZAghkrwtwO3K6VZRQXRCBOQwabYx4NKbQjuGnmC_pYggfHc2C6Q0VkmOGKOZD704S-1DdN_cthccumWQTw5Gv__xajwMOEc1Lzwmj8NfyjRFsBdj_g-J59yRnnmYe8ifnW41YJ6Hw",
"e": "AQAB"
},
{
"kid": "L7f-ZSULwQSZwsOvctCP4-7wToECMu9g110fnRrcGLw",
"kty": "RSA",
"alg": "RS256",
"use": "sig",
"x5c": [
"MIICpTCCAY0CBgGfaTbd9jANBgkqhkiG9w0BAQsFADAWMRQwEgYDVQQDDAtuaXNoaS1yZWFsbTAeFw0yNjA3MTYwNDM1MzFaFw0zNjA3MTYwNDM3MTFaMBYxFDASBgNVBAMMC25pc2hpLXJlYWxtMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtLA28oshmBdAIEcbe6AIIdKeNT3rU7HxQiq1wYQ6uq0C5kpP54JvH8YPD9Kf2+HRCB6cD7kexJKcpNIZs2pCSIc8/UdjiyjZe3y2X6GRYZR1llotUNHwnsBCkD19gU8GCzcMkPLSOWTntEQL6ovSjINtVC86GREAgrPAxQbciHeuHEagp/ngcCJtQJVHNYxssZGMHtn/v56QnST0z6EbelaXwGYqmDk7qsFKqseN0wMO5O9labWLtLVU7gr8aSjfwUSZ5/ADwsJ5pSM/N9cI52n/MBzveCUJYpmXjN9p6T3VphLqZU+eWtJvyK71NeFW/MdGxmLnZk41KQ4jhdF/xwIDAQABMA0GCSqGSIb3DQEBCwUAA4IBAQCbxEDV+wnk3Pjtb7QyECSWXK/j2E8UbYPYniIBNL1rDGfWCzy/j3J33Tso/INlSrRL6F7M1XiUs+U8lfErWq6d3rj27g0GKLGbtEDr12z7mum6FEs2T1MNlUrXfEiMFdpQJQzC4TpNFjMSXX4qW16GXMot1oUJYzZR7XY9QlfmhaGIehu5GgUk++RpRWnFY3qBmMrupBaW7KSKKvLAU7mr2dwpatHBo6f8QKqMKbVR+5lfW19ijn6JWGJbqe2zwraXB0p84kQyJKoDTCTgkAsSykOqoeQZ8EkFoffm2OV8sYSUCM6eMbq4lLSPYS7CcVldrSbPBr4ZWomjkeCmkQIo"
],
"x5t": "QwnqnkUGDjfDvlv442US2mPs7X4",
"x5t#S256": "vHrwY30Ly9KtGcpnPWhZPVg7TVw3RuS1O-eigs-DGd4",
"n": "tLA28oshmBdAIEcbe6AIIdKeNT3rU7HxQiq1wYQ6uq0C5kpP54JvH8YPD9Kf2-HRCB6cD7kexJKcpNIZs2pCSIc8_UdjiyjZe3y2X6GRYZR1llotUNHwnsBCkD19gU8GCzcMkPLSOWTntEQL6ovSjINtVC86GREAgrPAxQbciHeuHEagp_ngcCJtQJVHNYxssZGMHtn_v56QnST0z6EbelaXwGYqmDk7qsFKqseN0wMO5O9labWLtLVU7gr8aSjfwUSZ5_ADwsJ5pSM_N9cI52n_MBzveCUJYpmXjN9p6T3VphLqZU-eWtJvyK71NeFW_MdGxmLnZk41KQ4jhdF_xw",
"e": "AQAB"
}
]
}

1.A JWK Set is a JSON document published by the Identity Provider that 
contains one or more public keys. Each key has a unique kid (Key ID). 
When a JWT is received, Spring Security reads the kid from the JWT header,
finds the matching key in the JWK Set, constructs the RSA public key from
the JWK, and uses it to verify the JWT signature

2.Now from the given pasted example lets understand spring used /cert url and 
download key set now when access-token comes it uses kid from acces-token and matches 
with key set kid if anyone is matching then using that n and e it generates public key
Spring uses the public key to verify the JWT's digital signature.


3.https://jwt.io
paste access token in this URL and u will get decoded data
in the right side u get the three section first is header 
and header kid must match with

the last one is public key: which below things
{
"e": "AQAB",
"kty": "RSA",
"n": "tLA28oshmBdAIEcbe6AIIdKeNT3rU7HxQiq1wYQ6uq0C5kpP54JvH8YPD9Kf2-HRCB6cD7kexJKcpNIZs2pCSIc8_UdjiyjZe3y2X6GRYZR1llotUNHwnsBCkD19gU8GCzcMkPLSOWTntEQL6ovSjINtVC86GREAgrPAxQbciHeuHEagp_ngcCJtQJVHNYxssZGMHtn_v56QnST0z6EbelaXwGYqmDk7qsFKqseN0wMO5O9labWLtLVU7gr8aSjfwUSZ5_ADwsJ5pSM_N9cI52n_MBzveCUJYpmXjN9p6T3VphLqZU-eWtJvyK71NeFW_MdGxmLnZk41KQ4jhdF_xw"
}
kid is used only to identify which JWK to use.
The actual public key is constructed from n and e.

JWT has three part
HEADER.PAYLOAD.SIGNATURE

alg = RS256 → RSA SHA-256 signing algorithm.
kid → Key ID. This tells Spring Security which public key to use.