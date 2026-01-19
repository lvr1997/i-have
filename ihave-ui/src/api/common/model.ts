export interface CaptchaRequest {
    width: number,
    height: number,
    timestamp: number
}

export interface CaptchaResult {
    imgsrc: string
}