package com.gmoodle.auth;

public class JwtConfig {
	
	/*
	 * Esta llave se genera en la carpeta #15 video #14
	 * Posiblemente se necesite descargar el dll: msvcr120.dll
	 */
	public static final String RSA_PRIVADA = "-----BEGIN RSA PRIVATE KEY-----\r\n" + 
			"MIIEowIBAAKCAQEArsdcwXleiBXD6p5sGFcC4X11/LhfRSc56uvscNPrpZP7ggq0\r\n" + 
			"F3x3fEvsiXsdx+Ih/QcYkx6jC5cYClaiObN+fyWStpCcL1Y0KkHF8k6sTwRKLYXs\r\n" + 
			"1W3kONhiVfeEbrj6DY+FJ0Pos+Y79KXsFiys5KIBYkAB42TRkZtoTW3L1+bCueUD\r\n" + 
			"767yAShIh3Wce5hKdmqlVCPPAGUEcP9mowP2jhnyalD9fqwFH4ZvljNL8+xNBq1d\r\n" + 
			"PwiuqXCvqjnxXDGirPV2VWvb7DXjfcfImQWGKbYu95drnNDCvGsjPRsOE+iy6NCi\r\n" + 
			"iczWI8YOakOI326VJOrhsxHUDsR+myH2SS1RgwIDAQABAoIBAD3JPIEo5sI0742B\r\n" + 
			"c7UnDV2pfIxaoVNRCqiBvpQkVqmyJrdSEVg8KAFI1dXEMEL5LgrE92sK6ZivUVBW\r\n" + 
			"UAMV8SIBplvjw1L4pnhJyDBgn4lPeumc7pyEvKlb1WeOmMKLodopYmj/GMSAH7Hc\r\n" + 
			"9hjgsj4hB1w0RHaerXdL+RNmPSmVVCXvUCo89vZcj+BLobmnC8YCO4zHzbQS22mD\r\n" + 
			"MEzrRSoki9OoIPUHOn1WQDJtYz+DfGzfA5eGLj1SFGg625ahidyeW/T31G8xfog+\r\n" + 
			"4gHYUUs5qr4Hy6qLTOCfWWB1xv+75KL+EB4AP/PWa0FhYD4jwnjg85/SRBA1TN29\r\n" + 
			"ptNBuAECgYEA5n1bvVtsmmakwe74yj3PGZva5QMsj4JPHcrxN7rKY65RfpKyIfGn\r\n" + 
			"AV51lui5eY90ELMR1JMLYNuJsUnWsXUYzbPEQtOl6h3HpRT/tUBco+69N3V7tI0R\r\n" + 
			"dyVZIQTuougx9UKAD4+7AUjEhE1/zDbeKMsKCYV2WKIX685hoqbzbZsCgYEAwh+A\r\n" + 
			"5h64yC+DdPNo/3FQM+ROrFlqOeFwP8syq+aBgapIkDc5JjCkUBRUDlPeAJICLlsV\r\n" + 
			"V0YMF1AFNOR2TSVcXMXUpHFKFXEEXk064SDa4dZueE6h+Pr8NS6gN/enCEf4G10X\r\n" + 
			"b07ALK1/yBTYlLU/zDYgtM98rUDm8sQmHElhXjkCgYEAlrNsIjj+n01xeCOpo4Ia\r\n" + 
			"QOI4kvgqUIKg/BfO8+M7pUU6n4mG0s7PpQN/2vW7H5H+D1Ul2VlzXRbtxm9C4rBv\r\n" + 
			"UT+ymQcNOi/Sc0txja7RY5YbPYGhKoH5428WAZLZ/MXYtEWQuZv+Z26OnvqVDo7p\r\n" + 
			"Xr6yh1st7/G7tkAwckq6avUCgYBRm2rV0u59gbPy0209jcZYuk70l3ZXtNU6jFLV\r\n" + 
			"/0ZMW2i77+s/ic1ZuOIJT+qRNNBj6niTQuhMWKCHts+g6ObmPWiCZPnu/58FgdQR\r\n" + 
			"1n0N2daBDxV6lRC3zs0eGieHDLJZ1o3GhkW1wo5ob5MJGRUj5VFkkaFpOFKeliUr\r\n" + 
			"WRHsyQKBgFGqpkv736bMbYkjgDqHI69QddJW55K93CfMmyN/IPRsJYCL96/vbtnc\r\n" + 
			"DlGby6u8x0zZWDTxk4BB5cPeF7AQP+pEKonUModFcSrHqmR3w9O/xhRKzGA2VylF\r\n" + 
			"2KZ2CNatzgfpWXgrj29WqvXGFQ1H4nTCisJNfbZQ3tFTX4Vr65Ue\r\n" + 
			"-----END RSA PRIVATE KEY-----";
	
	public static final String RSA_PUBLICA = "-----BEGIN PUBLIC KEY-----\r\n" + 
			"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArsdcwXleiBXD6p5sGFcC\r\n" + 
			"4X11/LhfRSc56uvscNPrpZP7ggq0F3x3fEvsiXsdx+Ih/QcYkx6jC5cYClaiObN+\r\n" + 
			"fyWStpCcL1Y0KkHF8k6sTwRKLYXs1W3kONhiVfeEbrj6DY+FJ0Pos+Y79KXsFiys\r\n" + 
			"5KIBYkAB42TRkZtoTW3L1+bCueUD767yAShIh3Wce5hKdmqlVCPPAGUEcP9mowP2\r\n" + 
			"jhnyalD9fqwFH4ZvljNL8+xNBq1dPwiuqXCvqjnxXDGirPV2VWvb7DXjfcfImQWG\r\n" + 
			"KbYu95drnNDCvGsjPRsOE+iy6NCiiczWI8YOakOI326VJOrhsxHUDsR+myH2SS1R\r\n" + 
			"gwIDAQAB\r\n" + 
			"-----END PUBLIC KEY-----";
}