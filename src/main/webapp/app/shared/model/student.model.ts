export interface IStudent {
    id?: string;
    firstName?: string;
    lastName?: string;
}

export class Student implements IStudent {
    constructor(public id?: string, public firstName?: string, public lastName?: string) {}
}
