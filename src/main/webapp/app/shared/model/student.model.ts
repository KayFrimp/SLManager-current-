import { ILecture } from 'app/shared/model/lecture.model';

export interface IStudent {
    id?: string;
    firstName?: string;
    lastName?: string;
    enrolledOns?: ILecture[];
}

export class Student implements IStudent {
    constructor(public id?: string, public firstName?: string, public lastName?: string, public enrolledOns?: ILecture[]) {}
}
