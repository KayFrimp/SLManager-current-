export interface IProfessor {
    id?: string;
    firstName?: string;
    lastName?: string;
}

export class Professor implements IProfessor {
    constructor(public id?: string, public firstName?: string, public lastName?: string) {}
}
