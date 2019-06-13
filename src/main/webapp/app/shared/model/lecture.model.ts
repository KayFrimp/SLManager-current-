export interface ILecture {
    id?: string;
    courseName?: string;
    creditHours?: number;
}

export class Lecture implements ILecture {
    constructor(public id?: string, public courseName?: string, public creditHours?: number) {}
}
