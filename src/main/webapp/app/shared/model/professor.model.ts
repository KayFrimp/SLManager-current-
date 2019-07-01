import { ILecture } from 'app/shared/model/lecture.model';

export interface IProfessor {
    id?: string;
    firstName?: string;
    lastName?: string;
    lectures?: ILecture[];
}

export class Professor implements IProfessor {
    constructor(public id?: string, public firstName?: string, public lastName?: string, public lectures?: ILecture[]) {}
}
